
def call(Map args = [:]) {
    stage("Semantic release") {
        kubernetes {
            label "semantic-release"
            yaml """
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                  - name: container
                    image: artifactory.datapwn.com/docker-io-remote/node
                    command:
                    - cat
                    tty: true
                  imagePullSecrets:
                  - talend-registry
            """
        }
        environment {
            GH_TOKEN  = credentials('continuous-delivery')
        }
        steps {
            container("container") {
                script {
                    echo "semantic-release"
                    sh """
                        npm i -D semantic-release @semantic-release/changelog @semantic-release/git
                        npx semantic-release
                    """
                }
            }
        }
    }
}