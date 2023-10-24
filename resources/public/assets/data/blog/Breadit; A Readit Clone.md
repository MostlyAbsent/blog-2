title: Breadit, A Readit Clone
date: 2023-08-30
tags: projects
draft: false
summary: A Full Stack Reddit clone, built using React, Next.js; using planetscale datase, OAuth2 authentication, and upstash redis caching.

# Introduction

I recently followed @joshtriedcoding's amazing tutorial `Build and Deploy a Fullstack Reddit Clone: Next.js 13, React, Tailwind, Auth, Prisma, MySQL`.[^1][^2] It's my first large system dealing with React, Edge Computing, and TypeScript.

Check it out here https://breadit.just-the.tips.

# The Tutorial

It's presented as a nine hour long youtube video, covering all the business logic needed to created and deploy a lightweight clone of readit. It's very clearly presented and paced extremely well.

Starting the project I had never used TypeScript before, and I'd only tinkered with React[^3]. As a beginner it was very easy to follow. Modern lsp's make the tooling easy to get up and running, his starter code pre-loaded a large number of packages to support the project, which was particularly nice as finding and choosing libraries to solve problems on the fly is a separate challenge.

When errors occur (as is inevitable in a project this size) he adds the corrections in promptly, I rarely had an issue with the code that I was left to wonder about for more than a minute (and this was mostly due to my habit of saving every few seconds, so the hot reloading gave me faster feedback).

# The Breadit Project

## System structure

He designed the project to involve four edge compute systems. Vercel (next.js) for general compute, PlanetScale for the database, Uploadthing for file storage, and Upstash for redis caching.

This is an excellent choice of systems as all four are commonly used in industry[^4] so anyone seeking to learn well situated systems will benefit from these. The database interaction is handled by Prisma[^5] so replacing PlanetScale with an alternative would be a relatively trivial matter. The interaction with Upstash is also quite generic, so a different redis solution could be used easily. There is more specific logic needed to interact with Uploadthing, however this is isolated in it's own lib file, so changing to use S3 directly could be largely isolated from the front-end logic.

## Infinite Scrolling

He uses an infinite scrolling method for the post feeds, including paginated database calls. Its mechanism are very well explained and work fantastically, I can easily see myself using this pattern again and again.

## Libraries

He introduces and uses a number of very nice libraries that simplify the process of building the site.

### React Query

The mutate system here is a breeze to use to manage state, if you know the basics of a promise then you can easily extend that knowledge to state management. I expect I'll be using this for some time to come.

### Shadcn-UI

He uses this library for a lot of the more complex behavioural components in the site (dropdowns, searchbars, etc.,). I think this is a good choice as the focus of this tutorial is in the system not necessarily the UX design or CSS styling. These components are built from RadixUI and TailwindCSS, so extending either the design or the functionality remain viable. Replacing them with custom components should be straightforward and easiest if you adopt RadixUI[^6] and TailwindCSS[^7].

### Editorjs

This is a really nice prebuilt WYSIWYG editor that you can mostly just throw into your site to use. It's modular so if you want the code option you add that in there, and so forth. I've been thinking of adding an editor directly into this blog for a while - not sure I'll actually use it though - so it's nice to known there are plug and play solutions for this.

# Summary

In general it's an excellent project that I'd happily recommend.


[^1]: https://youtu.be/mSUKMfmLAt0?si=5SUtDLmLkT0eb6tW

[^2]: Check out that SEO and algorithm finessing.

[^3]: Through Clojure and ClojureScript. Some re-frame, mostly helix.

[^4]: Or in the case of uploadthing, should be commonly used. It's still new at the time of writing.

[^5]: Another commonly used industrial solution.

[^6]: I have not yet formed an opinion of RadixUI.

[^7]: I like tailwind, it makes sense to me.
