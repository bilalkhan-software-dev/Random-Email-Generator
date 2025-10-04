# Random Email Generator - Local Backend Setup

## Quick Start

Run your own backend in 2 simple steps:

### Step 1: Create docker-compose.yml file
Save this code as `docker-compose.yml` in any folder:

```yaml
services:
  postgres:
    image: postgres:15-alpine
    container_name: email-generator-db
    environment:
      POSTGRES_DB: random_email_generator
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: khan
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    restart: unless-stopped

  random-email-generator:
    image: bilalkhandevse/random-email-generator:latest
    container_name: random-email-app
    environment:
      - DB_URL=jdbc:postgresql://postgres:5432/random_email_generator
      - DB_USER=postgres
      - DB_PASSWORD=khan
      - FRONTEND_URL=https://random-email-generator.vercel.app
      - SERVER_PORT=8080
    ports:
      - "8081:8080"
    depends_on:
      - postgres
    restart: unless-stopped

volumes:
  postgres_data:
```

### Step 2: Run one command
Open terminal in the same folder and run:
```bash
docker-compose up -d
```

### Step 3: Use the application
Open **https://random-email-generator.vercel.app** in your browser

That's it! Your local backend is now running.

## What Just Happened?

- **Backend API** started on http://localhost:8081
- **PostgreSQL database** started on localhost:5432
- **Everything connected** to the online frontend
- **All data stored** locally on your computer

## Useful Commands

### Check if everything is running
```bash
docker ps
```

### View application logs
```bash
docker-compose logs -f
```

### Stop the application
```bash
docker-compose down
```

### Stop and delete all data
```bash
docker-compose down -v
```

### Update to latest version
```bash
docker-compose pull
docker-compose up -d
```

## Requirements

- **Docker Desktop** must be installed and running
- **Internet connection** (to download images first time)

## Troubleshooting

### If ports are already in use
Edit the `docker-compose.yml` file and change:
- `8080:8080` to `8081:8080` (or any free port)
- `5432:5432` to `5433:5432` (or any free port)

### If Docker is not running
- Make sure Docker Desktop is started
- Check system tray for Docker icon

### If frontend shows errors
- Check if backend is running: `docker ps`
- View backend logs: `docker-compose logs random-email-generator`

## Need Help?

Contact me: bilalkhan.devse@gmail.com.


---

**Note**: All database data is stored locally on your computer and remains private.