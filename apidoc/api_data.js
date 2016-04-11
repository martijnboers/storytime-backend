define({ "api": [
  {
    "type": "get",
    "url": "/up",
    "title": "Gives status of system and database connection",
    "name": "up",
    "group": "Index",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{ MESSAGE:\n\"yes plebian is running. Is database connection valid: true. time to prevent caching: 15:49:29\"\n, STATE: \"SUCCEEDED\" } }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/view/IndexRequest.java",
    "groupTitle": "Index"
  },
  {
    "type": "get",
    "url": "/user/info",
    "title": "Get basic info on user via token",
    "name": "info",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Header",
            "optional": false,
            "field": "Token",
            "description": "<p>The user's token.</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "SQLException",
            "description": "<p>If there is a db error.</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "UserDuplicate",
            "description": "<p>If the user already exist.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response: { MESSAGE:",
          "content": "\"Er is iets fout gegaan met de mentor toevoegen\" ,\nSTATE: \"ERROR\" } }",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\"MESSAGE\":{\"Type\":\"Child\",\"Username\":\"martijn\",\n\"Birthday\":2016-04-12,\"Gender\":\"m\",\"Name\":\"Martijn\"},\n\"STATE\":\"SUCCEEDED\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/view/UserRequest.java",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/user/register",
    "title": "Registers a user",
    "name": "register",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>Email adres.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>Username of user</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>User password</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "profilepicture",
            "description": "<p>ProfilePicture</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>Fullname of user</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "SQLException",
            "description": "<p>If there is a db error.</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "UserDuplicate",
            "description": "<p>If the user already exist.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response: { MESSAGE:",
          "content": "\"Er is iets fout gegaan met de mentor toevoegen\" ,\nSTATE: \"ERROR\" } }",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: { MESSAGE: \"Succesvol geregistreerd\"",
          "content": ", STATE: \"SUCCEEDED\" } }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/view/UserRequest.java",
    "groupTitle": "User"
  }
] });
