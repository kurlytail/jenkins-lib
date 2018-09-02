#!/usr/bin/env groovy

def call(String versionedJobName, String desiredBuildPrefix = '1.0', String buildsAllTime = null) {

    def buildPrefix = '0.0.' + env.JOB_NAME.replace('/', '.').replace(' ', '.') .replace('-', '.') + '.' + env.BUILD_NUMBER
    def versionNumber = buildPrefix

    if (env.JOB_NAME == versionedJobName) {
        env['BUILDING_QA_CANDIDATE'] = 'true'
        buildPrefix = desiredBuildPrefix
        if (buildsAllTime && buildsAllTime != '') {
            versionNumber = VersionNumber([versionNumberString: '.${BUILDS_ALL_TIME}-SNAPSHOT', versionPrefix: buildPrefix, buildsAllTime: buildsAllTime])
        } else {
            versionNumber = VersionNumber([versionNumberString: '.${BUILDS_ALL_TIME}-SNAPSHOT', versionPrefix: buildPrefix])
        }
    } else {
        env['BUILDING_QA_CANDIDATE'] = 'false'
        setupPipeline()
    }

    currentBuild.displayName = versionNumber
    return versionNumber
}
