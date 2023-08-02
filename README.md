### Download image 
```
docker run -d --restart always --name rabbitmq --hostname docker-rabbitmq -p 5672:5672 -p 15672:15672 -v d:/development/rabbitmq/docker/data:/var/lib/rabbitmq/mnesia rabbitmq:management
```

### Run on the localhost
http://localhost:15672/#/

```
version: '3'

services:
  rabbitmq:
    image: rabbitmq:management
    container_name: rabbitmq
    environment:
      - RABBITMQ_DEFAULT_USER=guest
      - RABBITMQ_DEFAULT_PASS=guest
    ports:
      - "5672:5672"
      - "15672:15672"

networks:
  default:
    driver: bridge
```

docker-compose up
docker-compose down

### URL
with header: http://localhost:8080/api/v1/mobile/ivo
without header: http://localhost:8080/api/v1/other/ivo

