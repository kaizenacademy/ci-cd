variable ami_name {
  type        = string
  default     = ""
  description = "Proved AMI id"
}

variable instance_type {
  type        = string
  default     = ""
  description = "Proved Instance type"
}

variable az1 {
  type        = string
  default     = ""
  description = "Proved availability zone"
}

variable key_pair {
  type        = string
  default     = ""
  description = "Proved key pair name"
}

variable region {
  type        = string
  default     = ""
  description = "Proved region"
}

data "aws_ami" "ubuntu" {
  most_recent = true
  filter {
    name   = "name"
    values = [var.ami_name]
  }
  owners = ["self"]
}

# variable ami_id {
#   type        = string
#   default     = "ami-092b51d9008adea15"
#   description = "Proved AMI id"
# }

# variable instance_type {
#   type        = string
#   default     = "t2.micro"
#   description = "Proved Instance type"
# }

# variable az1 {
#   type        = string
#   default     = "us-east-2a"
#   description = "Proved availability zone"
# }

# variable key_pair {
#   type        = string
#   default     = "my-laptop-key"
#   description = "Proved key pair name"
# }
