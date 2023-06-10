
template = '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: terraform
  name: terraform
spec:
  containers:
  - command:
    - sleep
    - "3600"
    image: hashicorp/terraform
    name: terraform'''


podTemplate(cloud: 'kubernetes', label: 'terraform', showRawYaml: false, yaml: template){
    node("terraform"){
    container("terraform"){
    stage("Pull repo"){
        git branch: 'main', url: 'https://github.com/kaizenacademy/ci-cd.git'
    }
    stage("Terraform Init"){
       sh 'terraform init'
}

withCredentials([usernamePassword(credentialsId: 'aws-creds', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
        stage("Terraform Apply"){
      sh 'terraform apply --auto-approve'
    }
}

    }
    
}
}










