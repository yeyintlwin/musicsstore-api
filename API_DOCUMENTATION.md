# Musics Store API Documentation

**Version:** 1.0.0  
**Base URL:** `http://localhost:8080`  
**Swagger UI:** [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)  
**OpenAPI JSON:** [`http://localhost:8080/api-docs`](http://localhost:8080/api-docs)

---

## Overview

Musics Store API is a RESTful backend for the Musics Store application.  
It provides full **CRUD** operations for 5 resources: **Music**, **Artist**, **Album**, **Genre**, and **Country**.

All list endpoints support:
- **Offset-based pagination** via `offset` and `limit` query parameters
- **Keyword search** via the `search` query parameter
- **Category ID filtering** on `/api/musics` (filter by `albumId`, `artistId`, `countryId`, `genreId`)

---

## Common Patterns

### Pagination Parameters

All `GET /api/{resource}` endpoints accept:

| Parameter | Type    | Required | Default | Description |
|-----------|---------|----------|---------|-------------|
| `offset`  | Integer | No       | 0       | Zero-based starting record index |
| `limit`   | Integer | No       | 10      | Maximum number of records to return |
| `search`  | String  | No       | —       | Keyword search (case-insensitive) |

### Paginated Response Structure

```json
{
  "content": [...],
  "totalElements": 100,
  "totalPages": 10,
  "size": 10,
  "number": 0
}
```

### HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200  | Success |
| 400  | Bad request (invalid body) |
| 404  | Resource not found |

---

## Resources

---

## 🎵 Music

**Base path:** `/api/musics`

### Music Object

```json
{
  "id": 1,
  "title": "Numb",
  "artist": { "id": 1, "name": "Linkin Park" },
  "genre":  { "id": 1, "name": "Rock" },
  "album":  { "id": 1, "name": "Meteora" },
  "country":{ "id": 1, "name": "USA" },
  "cover":  "https://example.com/covers/numb.jpg",
  "counter": 0,
  "link":   "https://example.com/music/numb.mp3",
  "createdDate":  "2024-01-01T00:00:00",
  "modifiedDate": "2024-01-01T00:00:00"
}
```

> **Note:** When creating or updating a music track, pass related entities as `{"id": 1}` — only the `id` field is required.

### Endpoints

---

#### `GET /api/musics` — Get all music tracks

**Query Parameters:**
In addition to the standard `offset`, `limit`, and `search` parameters, this endpoint supports optional filtering by related entities:

| Parameter   | Type | Required | Description                        |
|-------------|------|----------|------------------------------------|
| `albumId`   | Long | No       | Filter music tracks by Album ID    |
| `artistId`  | Long | No       | Filter music tracks by Artist ID   |
| `countryId` | Long | No       | Filter music tracks by Country ID  |
| `genreId`   | Long | No       | Filter music tracks by Genre ID    |

**Example Request:**
```
GET /api/musics?offset=0&limit=10&search=numb
```

**Response `200`:**
```json
{
  "content": [
    {
      "id": 1,
      "title": "Numb",
      "artist": { "id": 1, "name": "Linkin Park" },
      "genre":  { "id": 2, "name": "Rock" },
      "album":  { "id": 1, "name": "Meteora" },
      "country":{ "id": 5, "name": "USA" },
      "cover":  "https://example.com/covers/numb.jpg",
      "counter": 120,
      "link":   "https://example.com/music/numb.mp3",
      "createdDate":  "2024-01-01T00:00:00",
      "modifiedDate": "2024-01-10T12:00:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

---

#### `GET /api/musics/{id}` — Get a music track by ID

**Path Parameter:** `id` — Unique ID of the music track

**Response `200`:** Music object  
**Response `404`:** Music track not found

---

#### `POST /api/musics` — Create a new music track

**Request Body:**
```json
{
  "title": "In The End",
  "artist":  { "id": 1 },
  "genre":   { "id": 2 },
  "album":   { "id": 1 },
  "country": { "id": 5 },
  "cover":  "https://example.com/covers/intheend.jpg",
  "counter": 0,
  "link":   "https://example.com/music/intheend.mp3"
}
```

**Response `200`:** Created Music object

> `id`, `createdDate`, and `modifiedDate` are auto-generated — do not include them.

---

#### `PUT /api/musics/{id}` — Update a music track

**Path Parameter:** `id` — Unique ID of the music track  
**Request Body:** Same structure as POST (all fields updated)

**Response `200`:** Updated Music object  
**Response `404`:** Music track not found

---

#### `DELETE /api/musics/{id}` — Delete a music track

**Path Parameter:** `id` — Unique ID of the music track

**Response `200`:** Success  
**Response `404`:** Music track not found

---

## 🎤 Artist

**Base path:** `/api/artists`

### Artist Object

```json
{
  "id": 1,
  "name": "Linkin Park",
  "createdDate":  "2024-01-01T00:00:00",
  "modifiedDate": "2024-01-01T00:00:00"
}
```

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET`    | `/api/artists`      | Get all artists (paginated, searchable by `name`) |
| `GET`    | `/api/artists/{id}` | Get an artist by ID |
| `POST`   | `/api/artists`      | Create a new artist |
| `PUT`    | `/api/artists/{id}` | Update an artist's `name` |
| `DELETE` | `/api/artists/{id}` | Delete an artist |

**POST / PUT Request Body:**
```json
{ "name": "Linkin Park" }
```

---

## 💿 Album

**Base path:** `/api/albums`

### Album Object

```json
{
  "id": 1,
  "name": "Meteora",
  "createdDate":  "2024-01-01T00:00:00",
  "modifiedDate": "2024-01-01T00:00:00"
}
```

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET`    | `/api/albums`      | Get all albums (paginated, searchable by `name`) |
| `GET`    | `/api/albums/{id}` | Get an album by ID |
| `POST`   | `/api/albums`      | Create a new album |
| `PUT`    | `/api/albums/{id}` | Update an album's `name` |
| `DELETE` | `/api/albums/{id}` | Delete an album |

**POST / PUT Request Body:**
```json
{ "name": "Meteora" }
```

---

## 🎸 Genre

**Base path:** `/api/genres`

### Genre Object

```json
{
  "id": 1,
  "name": "Rock",
  "createdDate":  "2024-01-01T00:00:00",
  "modifiedDate": "2024-01-01T00:00:00"
}
```

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET`    | `/api/genres`      | Get all genres (paginated, searchable by `name`) |
| `GET`    | `/api/genres/{id}` | Get a genre by ID |
| `POST`   | `/api/genres`      | Create a new genre |
| `PUT`    | `/api/genres/{id}` | Update a genre's `name` |
| `DELETE` | `/api/genres/{id}` | Delete a genre |

**POST / PUT Request Body:**
```json
{ "name": "Rock" }
```

---

## 🌏 Country

**Base path:** `/api/countries`

### Country Object

```json
{
  "id": 1,
  "name": "Myanmar",
  "createdDate":  "2024-01-01T00:00:00",
  "modifiedDate": "2024-01-01T00:00:00"
}
```

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET`    | `/api/countries`      | Get all countries (paginated, searchable by `name`) |
| `GET`    | `/api/countries/{id}` | Get a country by ID |
| `POST`   | `/api/countries`      | Create a new country |
| `PUT`    | `/api/countries/{id}` | Update a country's `name` |
| `DELETE` | `/api/countries/{id}` | Delete a country |

**POST / PUT Request Body:**
```json
{ "name": "Myanmar" }
```

---

## Database Schema

```
artists    : id, name, created_date, modified_date
albums     : id, name, created_date, modified_date
genres     : id, name, created_date, modified_date
countries  : id, name, created_date, modified_date
musics     : id, title, artist (FK), genre (FK), album (FK), country (FK),
             cover, counter, link, created_date, modified_date
```

---

## Quick Reference

| Resource | GET (list) | GET (by id) | POST | PUT | DELETE |
|----------|-----------|-------------|------|-----|--------|
| Music    | `GET /api/musics` | `GET /api/musics/{id}` | `POST /api/musics` | `PUT /api/musics/{id}` | `DELETE /api/musics/{id}` |
| Artist   | `GET /api/artists` | `GET /api/artists/{id}` | `POST /api/artists` | `PUT /api/artists/{id}` | `DELETE /api/artists/{id}` |
| Album    | `GET /api/albums` | `GET /api/albums/{id}` | `POST /api/albums` | `PUT /api/albums/{id}` | `DELETE /api/albums/{id}` |
| Genre    | `GET /api/genres` | `GET /api/genres/{id}` | `POST /api/genres` | `PUT /api/genres/{id}` | `DELETE /api/genres/{id}` |
| Country  | `GET /api/countries` | `GET /api/countries/{id}` | `POST /api/countries` | `PUT /api/countries/{id}` | `DELETE /api/countries/{id}` |
