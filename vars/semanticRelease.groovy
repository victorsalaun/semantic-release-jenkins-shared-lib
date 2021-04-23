def call(Map args = [:]) {

    withCredentials([string(credentialsId: 'continuous-delivery', variable: 'GH_TOKEN')]) {
        podTemplate(label: "semantic-release",
                yaml: """\
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
            """.stripIndent()) {
            node("semantic-release") {
                container('container') {
                    sh """
                        npm i -D semantic-release @semantic-release/changelog @semantic-release/git
                        npx semantic-release
                    """
                }
            }
        }
    }

}