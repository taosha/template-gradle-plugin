package org.taosha.gradle.plugin.template

import groovy.text.SimpleTemplateEngine
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * Created by San on 15/2/3.
 */
class TemplatePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task('generateSources', type: GenerateTask) {
            group = 'Generator'
            description = 'Generate sources from templates'
        }

        def generate;
        try {
            generate = project.tasks['generate']
        } catch (e) {
            generate = project.task('generate') { group = 'Generator' }
        }
        generate.dependsOn('generateSources')

        project.extensions.create('template', TemplateExtension);
    }

}

class GenerateTask extends DefaultTask {
    static final engine = new SimpleTemplateEngine()

    @TaskAction
    def generateSources() {
        project.template.works.each {
            project.file(it['output']).parentFile?.mkdirs()
            engine.createTemplate(project.file(it['template']))
                    .make(it['data'])
                    .writeTo(new FileWriter(project.file(it['output'])))
        }
    }
}

class TemplateExtension {
    final works = []

    def generate(Map<String, Object> args) {
        works << args
    }
}

