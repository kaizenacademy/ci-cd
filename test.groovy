
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

tfvars = '''
ami_id="ami-092b51d9008adea15"
instance_type="t2.micro"
az1="us-east-2a"
key_pair="my-laptop-key"
'''


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
          writeFile file: 'hello.tfvars', text: tfvars
      sh 'terraform apply -var-file hello.tfvars --auto-approve'
    }
}

    }
    
}
}
