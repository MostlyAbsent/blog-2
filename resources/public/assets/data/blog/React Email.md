title: React Email
date: 2023-10-04
tags: technology
draft: true
summary: A look at React Email.

# Introduction

Creating attractive emails in the current (2023) landscape is quite difficult given the esoteric version of HTML used in rendering email. React Email abstracts these implementation details away allowing you to design and write your emails using React and TypeScript to more easily keep a consistent design language across your digital media.

# Setup and Dev environment

React email has an automatic deployment tool available from your favourite npm equivalent. Including a hot reloading dev server. All the basic niceties one would expect from a modern tool expecting any level of widespread adoption.

# React Email Components

Instead of using HTML elements directly you use the components provided by the `@react-email` package. All the magic needed to ensure the email renders the component in the expected fashion is handled by these primitives.

# Sending the email

The emails you've designed in this system need to be compiled to a HTML string that your email provider can consume, `@react-email/render` provides this compilation function. This string can then be dispatched to your provider by whatever normal means they have.

## Data flow
A call to your email provider's send function requires something like 

```
{:to address@email.com
 :from address@email.com
 :subject "Here is your example email"
 :content <-- some string renderable by the email client -->}
```

React Email provides the framework for the `content`, which is finalised by the `render` function.

# Conclusion

React Email is easy to include into a design system, workflow and build system. Leveraging the vast community of React developer to rapidly generate quality email materials. I'd likely suggest it to most environments as viable solution.
