#install yum
#

#install git with
#sudo su
# yum install git 

#run as sudo instals docker and docker compose then builds cluster in AWS, made for quickly doing this 
#bask aws_setup_file.sh
yum install docker
yum install -y yum-utils device-mapper-persistent-data lvm2
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
yum install docker-ce
systemctl enable docker.service
systemctl start docker.service
yum install epel-release
yum install -y python-pip
pip install docker-compose
yum upgrade python*



