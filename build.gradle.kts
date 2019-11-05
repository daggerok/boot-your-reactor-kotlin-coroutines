plugins {
    idea
    base
}

allprojects {
    group = Globals.groupId
    version = Globals.version
}

defaultTasks("clean", "build")
