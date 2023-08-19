# UI Wishlist

## Public views

### Landing page

- NavBar with `article category` buttons and a login/register/logout button
    - > Three `category buttons` are considered _built-in_ and always present
      > - homilies
      > - articles (generic)
      > - schedules
    - > In the app lifecycle a category is always "selected/opened" and one of the above built ins
      is the default
    - > For administrators two additional buttons are present:
      > - an `archived` category button, to inspect archived articles
      > - a `new category button`: clicking it opens a modal dialog to enter the desired name for
          the new category
- Clicking on any of the categories in the NavBar displays a blog-like list of relevant articles
  under it ( `CategoryView`)
    - Articles are displayed as preview cards: with a thumbnail, title, description and issue date
    - `CategoryView` features two additional buttons when viewed as administrator:
      > - `delete category`: deletes the category and all of its articles are moved to archived
      > - `upload new article`: Starts the `UploadView` in this category
    - Clicking an article navigates to its own page where its full text and illustrations can be
      read ( `ArticleView` )
      > - anonymous and regular users may only have a back button if necessary
      > - administrators can have a `delete article` and `reupload article` button

## Admin Views

### UploadView

Facilitates uploading a Word document to serve as the basis of the article (drag-and-drop maybe?)
and a preview

### ScheduleView

Schedules have a title, valid from-to dates and a short description.
Schedule elements are displayed in a table:

| time  | type    | title         | comment   |
|-------|---------|---------------|-----------|
| 12:00 | meeting | Weeky StandUp | be sharp! |
| 12:00 | meeting | Weeky StandUp | be sharp! |

Clicking an an article item in the built-in schedules category shows this header and table.

### UploadScheduleView

Schedules may be uploaded by filling in a form with title, description the dates and a list of form
inputs corresponding to the above fields of the above table.

