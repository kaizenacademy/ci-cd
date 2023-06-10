# provider "aws" {
#   region = "us-east-2"
# }

# resource "aws_instance" "web" {
#   ami                    = var.ami_id
#   instance_type          = var.instance_type
#   availability_zone      = var.az1
#   key_name               = var.key_pair
# }


provider "aws" {
region = "us-east-2"
}


resource "aws_instance" "web" {
ami = "ami-092b51d9008adea15"
instance_type = "t2.micro"
availability_zone = "us-east-2a"
key_name = "my-laptop-key"
}