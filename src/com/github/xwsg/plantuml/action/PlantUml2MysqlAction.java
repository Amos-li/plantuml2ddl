package com.github.xwsg.plantuml.action;

import com.github.xwsg.plantuml.generator.PlantUml2MysqlDdlGenerator;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;

/**
 * Generate MySQL DDD from PlantUML Action.
 *
 * @author xwsg
 */
public class PlantUml2MysqlAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile plantUmlFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        Project project = e.getData(PlatformDataKeys.PROJECT);

        if (project != null && plantUmlFile != null) {
            // Show background process indicator
            ProgressManager
                .getInstance().run(new Task.Backgroundable(project, "DDL Generation", false) {
                @Override
                public void run(@NotNull ProgressIndicator indicator) {
                    // Generate DDLs
                    new PlantUml2MysqlDdlGenerator().generate(plantUmlFile);
                    // refresh
                    VirtualFileManager.getInstance().asyncRefresh(null);
                }
            });
        }
    }
}