terraform {
  backend "s3" {
    bucket         = "jenkins-terraform-kaizen"
    key            = "terraform.tfstate"
    region         = "us-east-2"
  }
}
