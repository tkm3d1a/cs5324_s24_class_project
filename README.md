# The Incredibles

## Spring '24 CSI 5324 Class Project

## Team Members

- Tim Klimpel
- Pawan Bhandari
- Matthew Hays
- Sarah Faron

## Project Description

<!-- TODO: Populate with short description of which ever project is choosen -->

### Project Startup/Execution Steps
#### *Run the following steps in order to start the program.*

- Navigate to the `/CS_department_monitor_backend` directory.
- `mvn clean`
- `docker compose up`
- `mvn spring-boot:run`

#### *To execute tests*

- `docker compose up`
- `mvn spring-boot:run`
- `mvn clean test`

**NOTE: The application server *must* be executing to run tests.**

#### *To run the client server*

- Navigate to the `/cs5324-frontend` directory.
- `npm run build` or `npm run dev`
- Visit `http://localhost:3000`

#### *Endpoint requirements*

- /api/display
  - GET /media - Returns a list of media to display.
  - POST /media - Update a medai item for tagging.
    - Requires an `IdListDTO` object in the Request Body.
  - GET /posts - Returns a list of posts to display.
  - POST /posts - Tags a post for display.
    - Requires a `IdListDTO` object in the Request Body.
- /api/events
  - GET / - Returns a list of events owned by the user.
    - Requires a UserId to be given as a path parameter.
  - POST / - Creates a new post and its associated Notification.
    - Requires an `Event` object on the Request Body.
  - GET /selected - Returns a selected `Event`.
    - Requires an event id as a path parameter.
  - PATCH /selected/edit - Edits an existing event.
    - Requires an `Event` on the request body.
  - DELETE / - Deletes an event.
    - Requires an `Event` on the request body.
- /api/media
  - GET / - Returns a sorted list of media.
  - GET /{mediaId} - Returns a selected media item.
    - Requires a media id as a request parameter.
  - POST / - Creates a new media item and associated Notification.
    - Requires a `MediaDTO` object on the request body.
  - POST /populate - Populates the database with sample data.
  - PATCH /{mediaId} - Updates an existing media item.
    - Requires a media id as a path parameter and a `MediaDTO` on the request body.
  - DELETE /{mediaId} - Deletes a selected media item.
    - Requires a media id as a path parameter.
- /api/posts
  - POST / - Creates a new post and associated Notification.
    - Requires a `Post` object on the request body.
  - GET / - Gets a list of posts for the requesting user.
    - Requires a user id as a request parameter.
  - DELETE /{id} - Deletes a selected post.
    - Requires a post id as a request parameter.
  - PATCH / - Updates an existing post.
    - Requires a `Post` on the request body.
  - GET /populate - Populates the database with sample posts.
  - POST /populate/approved - Populates the database with sample approved posts.
    - Requires a desired post count as a request parameter.
  - GET /all - Returns all posts sorted on the created at attribute.
- /api/reviews
  - GET / - Returns a list of non-approved notifications sorted in descending order on the created at attribute.
  - POST / - Allows for the approval/rejection of entities associated with the given Notification.
    - Requires a boolean for the isApproved request parameter and a `Notification` object on the request body.

## Repository Structure

```
.
`-- "Repo Home"
    |-- project_artifacts
    |   |-- iteration_1
    |   |-- iteration_2
    |   `-- iteration_3
    |-- project_managemen
    |-- source_code
    |   |-- backend
    |   |-- db
    |   |-- frontend
    |   `-- misc
    |-- zz_examples
    `-- zz_original_images
```
