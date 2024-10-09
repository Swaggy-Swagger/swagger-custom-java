# Contributing to Swaggy-Swagger

Thank you for your interest in contributing to Swaggy-Swagger!

Good places to start are :
-   Reading this document

There are several ways you can contribute to Swaggy-Swagger!

- Troubleshoot problems that existed code.
- Submit Bug/Feature issues related to bugs or desired new features.
- Submit Documentation issues for insufficient documents, translations.
- Start conversation at Discussions to provide a good example of preset.
- Start conversation by posting to Discussions about a framework that needs support.
- If there's anything you'd like to communicate about our project or open source, feel free to post it on Discussions!  
  "We hope that Swaggy-Swagger Discussions will become an active community."

## Working on Issues

Feel free to ask for guidelines on how to tackle a problem on [email][email] or open a
[new issue][new-issues]. This is especially important if you want to add new
features to Swaggy-Swagger or make large changes to the already existing code-base.
The Swaggy-Swagger core developers will do their best to provide help.

If you start working on an already-filed issue, post a comment on this issue to
let people know that somebody is working it. Feel free to ask for comments if
you are unsure about the solution you would like to submit.

We use the "fork and pull" model [described here][development-models], where
contributors push changes to their personal fork and create pull requests to
bring those changes into the source repository.

Steps to get started :

-   Fork Swaggy-Swagger and create a branch from main for the issue you are working on.
-   Please ensure that your development environment is set up, including Java/Spring Boot, and the required utilities are installed before you start development. 
-   Please adhere to the code style that you see around the location you are
    working on.
-   Commit as you go.
-   Push your commits to GitHub and create a pull request against the branch of `swagger-custom-java`.

### 🐞 Bug Reports

We can't fix what we don't know about, so please report problems. This
includes problems with understanding the documentation, unhelpful error messages,
and unexpected behavior.

You can open a new issue by following [this link][new-issues] and choose one of the issue templates.
Even better, you can submit a Pull Request with a fix.

### ✅ Feature Requests

Please feel free to open an issue using the [feature request template][new-issues].

### 📕 Contributing to the Documentation

We're always looking for improvements to the documentation that make it more clear and accurate.
Please let us know how the documentation can be improved such as typos and any content that is missing, unclear or inaccurate.
We'll be happy to make the changes or help you make a contribution if you're interested!

You can create an issue using the [documentation issue template][new-issues].

### Issue Triage

Sometimes an issue will stay open, even though the bug has been fixed. And
sometimes, the original bug may go stale because something has changed in the
meantime.

It can be helpful to go through older bug reports and make sure that they are
still valid. Load up an older issue, double check that it's still true, and
leave a comment letting us know if it is or is not. The [least recently
updated sort][lru] is good for finding issues like this.

<br>

---

## Submitting a Pull Request (PR)
>_If you want to contribute to the frontend (UI), please refer to [swaggy-ui](https://github.com/Swaggy-Swagger/swaggy-ui?tab=readme-ov-file#ways-to-contribute) for more details._

Before you submit your Pull Request (PR) consider the following guidelines :

  1. To avoid duplicating works, please check GitHub Pull Requests for any existing open or closed PRs related to your submission before you start.

  2. Fork this repository to your GitHub - 
   [click to fork swagger-custom-java](https://github.com/Swaggy-Swagger/swagger-custom-java/fork)

   3. Clone your swagger-custom-java repository :
      ```bash
        git clone https://github.com/{your-github-id}/swagger-custom-java
      ```

  4. Make your changes in a main branch or feature branch.

  5. Follow our [STYLE_GUIDE](https://github.com/Swaggy-Swagger/swagger-custom-java/blob/main/STYLE_GUIDE.md). <br>
   _This is simply a recommendation, so please feel free to contribute!_

  6. Commit your changes using a descriptive commit message that follows our commit message conventions.

  7. Push your branch to GitHub :

     ```bash
      git push origin your-branch
     ```

  8. In GitHub, send a pull request to `swagger-custom-java:main` or `swagger-custom-java:feature/\* `.  
  
  > That's it! Thank you for your contribution! 😎 

<br> 

After the pull request is made, one of the Swaggy-Swagger project developers will review your code.
The review-process will make sure that the proposed changes are sound.
Please give the assigned reviewer sufficient time, especially during weekends.
If you don't get a reply, you may ping the core developers on [email][email]. 

A merge of Swaggy-Swagger's main-branch and your changes is immediately queued
to be tested after the pull request is made. In case unforeseen
problems are discovered during this step (e.g. a failure on a platform you
originally did not develop on), you may ask for guidance. Push additional
commits to your branch to tackle these problems.

The reviewer might point out changes deemed necessary. Please add them as
extra commits; this ensures that the reviewer can see what has changed since
the code was previously reviewed. Large or tricky changes may require several
passes of review and changes.

Once the reviewer approves your pull request, a friendly bot picks it up
and merges it into the Swaggy-Swagger `main` branch.


[new-issues]: https://github.com/Swaggy-Swagger/swagger-custom-java/issues/new/choose
[email]: mailto:clcc001@naver.com
[development-models]: https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/getting-started/about-collaborative-development-models
[lru]: https://docs.github.com/en/search-github/getting-started-with-searching-on-github/sorting-search-results#sort-by-updated-date
