
// Generic setup for all requests

// First we will not let concurrent jobs run. Ie abort previous jobs and let only the latest one run
def call() {

    def hi = Hudson.instance
    def pname = env.JOB_NAME.split('/')

    hi.getItem(pname[0]).getItem(pname[1]).getItem(pname[2]).getBuilds().each{ build ->
        def exec = build.getExecutor()

        if (build.number < currentBuild.number && exec != null) {
            exec.interrupt(
                Result.ABORTED,
                new CauseOfInterruption.UserInterruption(
                    "Aborted by #${currentBuild.number}"
                )
            )
            build.setDescription("Aborted by #${currentBuild.number}")

            println("Aborted previous running build #${build.number}")
        } else {
            println("Build is not running or is current build, not aborting - #${build.number}")
        }
    }
}
