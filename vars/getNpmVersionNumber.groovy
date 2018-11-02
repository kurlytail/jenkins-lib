#!/usr/bin/env groovy

def call(String versionedJobName, String desiredBuildPrefix = '1.0', String buildsAllTime = null) {

    def versionNumber = '99.99.99.' + env.JOB_NAME.replace('/', '.').replace(' ', '.') .replace('-', '.') + '.' + env.BUILD_NUMBER

    if (env.JOB_NAME == versionedJobName) {
        env['BUILDING_QA_CANDIDATE'] = 'true'
        if (buildsAllTime) {
            versionNumber = VersionNumber([versionNumberString: '.${BUILDS_ALL_TIME}', versionPrefix: desiredBuildPrefix, buildsAllTime: buildsAllTime])
        } else {
            versionNumber = VersionNumber([versionNumberString: '.${BUILDS_ALL_TIME}', versionPrefix: desiredBuildPrefix])
        }
    } else {
        env['BUILDING_QA_CANDIDATE'] = 'false'
        versionNumber = '99.99.99'
    }

    currentBuild.displayName = versionNumber
    return versionNumber
}

