echo "building bff"
cd bff && sh build.sh
cd ..
echo "building customer service"
cd customer-service && sh build.sh
cd ..
echo "starting services"
docker-compose up -d