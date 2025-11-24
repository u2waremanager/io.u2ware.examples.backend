# What you needs

| Keyword | Description |
| --- | --- | 
| [REPOSITORY] | REPOSITORY |
| [REPOSITORY_DIR] | REPOSITORY |



# Runtime Envirement 

#### 1. 

> git clone [REPOSITORY]

```bash
git clone https://github.com/u2waremanager/io.u2ware.common.usage.git
```

#### 2. 
> cd [REPOSITORY_DIR]

```bash
cd io.u2ware.common.usage
```

#### 3. 

```bash
docker-compose up -d
```

#### 4.
> http://localhost:8080


# Development Envirement 

## Develop 

### 1. Backend

#### 1-1. 

>  cd [REPOSITORY_DIR]

```bash
cd io.u2ware.common.usage
```

#### 1-2. 
```bash
./mvnw spring-boot:run
```


### 2. Frontend


#### 2-1. 
> cd [REPOSITORY_DIR]/src/test/resources/frontend

```bash
cd io.u2ware.common.usage/src/test/resources/frontend
```

#### 2-2. 
```bash
npm install
```

#### 2-3. 
```bash
npm run dev
```

## Package 

### 1. Backend
> cd src/test/resources/frontend

> npm run build


## Package Backend

> cd {source}
> ./mvnw package


## Build Image

> docker build \
  --no-cache \
  --platform linux/arm64,linux/amd64 \
  --tag ${app_name}:${app_version} \
  .

## Deploy Image

> docker push ....






docker build -t my-image:1.0 .



<!-- > docker run -d \
-p 5432:5432 \
-p 8080:8080 \
-e POSTGRES_HOST_AUTH_METHOD=trust \
-e POSTGRES_DB=postgres \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
--name my-image \
my-image:1.0 -->


> docker build -t bookstore-app .


> docker exec -it ???? /bin/bash