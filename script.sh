name_suffix="Khanjan"
# cd to root dir
cd /home/centos/dev/jarvis_data_eng_${name_suffix}
# create java_apps project direcotry
cd core_java
#update dev branch
git checkout develop
git pull
# create a sub-direcotory for each app
mkdir grep jdbc twitter
touch README.md
# inti README.md
cat > README.md << EOF
# Core Java Apps
This project consists of three Java applications below

1. [Java Grep App](./grep)
2. [JDBC App](./jdbc)
3. [TwitteCRUD App](./twitter)
EOF

for dir in $(ls -d */); do
touch $dir/pom.xml;
touch $dir/README.md;
mkdir -p $dir/src/test/java/ca/jrvs/apps/$dir
mkdir -p $dir/src/main/java/ca/jrvs/apps/$dir
done

