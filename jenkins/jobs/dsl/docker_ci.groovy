import pluggable.scm.*

SCMProvider scmProvider = SCMProviderHandler.getScmProvider("${SCM_PROVIDER_ID}", binding.variables)

//Folders
def workspaceFolderName = "${WORKSPACE_NAME}"
def projectFolderName = "${PROJECT_NAME}"
def projectScmNamespace = "${SCM_NAMESPACE}"

// Variables
def dockerfileGitRepo = "adop-cartridge-docker-reference"

// Jobs
def dockerci = pipelineJob(projectFolderName + "/Docker_CI")

dockerci.with {
    definition {
         cps {
               	script('''
               	|node('docker') {
				|   stage('GetDockerfile') {
				|   scm '''.stripMargin() + scmProvider.get(projectScmNamespace, dockerfileGitRepo , "*/master", "adop-jenkins-master", null) + '''
				|   }
				|   stage('DockerCodeAnalysis') {
				|   }
				|   stage('DockerBuild') {
				|   }
				|   stage('DockerSecurityTest') {
				|   }
				|   stage('DockerBDDTest') {
				|   }
				|   stage('DockerPush') {
				|   }
			   	|}
                |'''.stripMargin())
               sandbox()
         }
    }
}
