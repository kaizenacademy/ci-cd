
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


properties([parameters([choice(choices: ['apply', 'destroy'], description: 'Pick the action:', name: 'action')])])

properties([parameters([choice(choices: ['us-east-1', 'us-east-2', 'us-west-1', 'us-west-2'], description: 'Please pick the region', name: 'region')])])

podTemplate(cloud: 'kubernetes', label: 'terraform', showRawYaml: false, yaml: template){
    node("terraform"){
    container("terraform"){
    stage("Pull repo"){
        git branch: 'main', url: 'https://github.com/kaizenacademy/ci-cd.git'
    }
    

withCredentials([usernamePassword(credentialsId: 'aws-creds', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
        stage("Terraform Init "){
       sh 'terraform init -backend-config="key=us-east-2/terraform.tfstate"'
}       
if(params.action == "apply") {

        stage("Terraform Apply"){
          writeFile file: 'hello.tfvars', text: tfvars
      sh 'terraform apply -var-file hello.tfvars --auto-approve'
    }
}
else {
    stage("Terraform Destroy"){
          writeFile file: 'hello.tfvars', text: tfvars
      sh 'terraform destroy -var-file hello.tfvars --auto-approve'
    }
}
}
    }  
}
}
