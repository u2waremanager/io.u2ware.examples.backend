# What you needs

| Keyword | Description |
| --- | --- | 
| ... | ... |


# Runtime Environment 


# Development Environment

```bash
git clone https://github.com/u2waremanager/io.u2ware.examples.backend.git
```

```bash
cd io.u2ware.examples.backend
```

```bash
docker-compose up -d 
```

```bash
./mvnw spring-boot:run
```

```bash
http://localhost:8080
```

# Distribution Environment

```bash
docker build \
--platform linux/arm64,linux/amd64 \
-t ghcr.io/u2waremanager/io.u2ware.examples.backend:0.0.1-SNAPSHOT \
.
```

```bash
docker push ghcr.io/u2waremanager/io.u2ware.examples.backend:0.0.1-SNAPSHOT
```
