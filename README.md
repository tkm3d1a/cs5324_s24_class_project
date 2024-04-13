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
  - GET ?id - Returns a list of events owned by the user.
    - Requires a UserId to be given as a path parameter.
  - POST / - Creates a new post and its associated Notification.
    - Requires an `Event` object on the Request Body.
  - GET /selected?id retrieves the selected event.
    - Needs a valid event with given id to exist in the db, else returns EventDoesNotExistException.
  - PATCH /selected/edit edits the event in the db with matching eventId
    - Needs a valid event with given id to exist in the db, else returns EventDoesNotExistException.
  - DELETE / deletes the event in the db with matching eventId
    - Needs a valid event with given id to exist in the db, else returns EventDoesNotExistException.
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
  - GET ?userId - retrieves all posts created by the user.
    - Needs a valid userID that exists in the DB otherwise throws a NotFoundException
  - DELETE /{postId} deletes a post.
    - postID must be a valid UUID string but its not necessary for post with that postID to exit, returns 200 OK regardless.
  - PATCH / updates the post and returns the updated post.
    - Needs a valid postID on the request body that exists in the DB otherwise throws a NotFoundException
  - GET /populate can be used to populate the DB with a user with 2 posts. The response has the post details with postID and userID which can be used to test the get, delete and patch methods.
  - POST /populate/approved - Populates the database with sample approved posts.
    - Requires a desired post count as a request parameter.
  - GET /all - Returns all posts sorted on the created at attribute.
- /api/reviews
  - GET / - Returns a list of non-approved notifications sorted in descending order on the created at attribute.
  - POST ?isApproved - Allows for the approval/rejection of entities associated with the given Notification.
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
