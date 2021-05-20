podTemplate(yaml: """\
    apiVersion: v1
    kind: Pod
    spec:
      containers:
      - name: 3scale-toolbox
        image: quay.io/redhat/3scale-toolbox:master
        command:
        - cat
        tty: true
        env:
        - name: HOME
          value: /config
        volumeMounts:
        - name: toolbox-config
          mountPath: /config
      volumes:
      - name: toolbox-config
        secret:
          secretName: 3scale-toolbox
    """.stripIndent()) {

    node(POD_LABEL) {
      container('3scale-toolbox') {
        sh '3scale --version'
        sh '3scale -k service list 3scale-kskels'
      }
    }

}

