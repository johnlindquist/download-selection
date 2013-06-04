package com.johnlindquist.plugins

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.ide.BrowserUtil
import com.intellij.util.download.impl.DownloadableFileServiceImpl
import com.intellij.util.download.DownloadableFileService
import com.intellij.util.download.impl.DownloadableFileSetDescriptionImpl
import com.intellij.util.download.FileDownloader
import com.intellij.util.download.impl.FileDownloaderImpl
import com.intellij.util.download.DownloadableFileDescription
import com.intellij.util.download.impl.DownloadableFileDescriptionImpl
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.util.ui.UIUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.actionSystem.PlatformDataKeys
import java.net.URL
import java.nio.channels.Channels
import java.io.FileOutputStream
import com.intellij.util.treeWithCheckedNodes.SelectionManager
import com.intellij.openapi.editor.impl.CaretModelImpl
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ide.SaveAndSyncHandlerImpl
import com.intellij.openapi.fileEditor.FileDocumentManager

open class DownloadSelection(): AnAction() {
    public override fun actionPerformed(e: AnActionEvent?): Unit {
        val editor = e?.getData(PlatformDataKeys.EDITOR)
        val path = e?.getData(PlatformDataKeys.VIRTUAL_FILE)?.getParent()?.getPath()
        val urlString = editor?.getSelectionModel()?.getSelectedText()
        val int:Int = urlString?.lastIndexOf("/")!!
        val substring = urlString?.substring(int, urlString?.length!!)


        val downloader = DownloadableFileService.getInstance()
        val description = downloader?.createFileDescription(urlString!!, substring!!);
        val files = downloader?.createDownloader(listOf(description), e?.getProject(), editor?.getComponent(), substring)
                ?.toDirectory(path!!)?.download();
    }
}
