title: Sudoku, The Project
date: 2023-11-29 
tags: technology
draft: false
summary: Developed and launched a Sudoku app, navigating solver intricacies, evolving data models, and tackling Nginx complexities for deployment. 

# Sudoku, The Project

I've recently launched the [Sudoku app](http://sudoku.just-the.tips), exploring three interesting aspects; the solver element, the data model, and the deployment using reverse proxy/docker.

## Sudoku Solver
Exploring programmatic sudoku solvers led me to an 11-year-old example[^3] that required updating to run on modern Clojure due to outdated set mathematics module requirements. Despite finding smaller functional approaches, this straightforward and performant solver required very little adaptation to integrate with the UI's data model.

## Evolution of the Data Model

Crafting the data model using Clojure structures and React components posed challenges, but I aimed for atomic data storage to ensure consistent updates during gameplay. Iterations refining the grid's structure, from arrays of maps to a simplified array of indexed pairs, culminated in a Jotai atom setup, using Jotai's large object handling to pass smaller data to React components for efficient updates.

## Navigating Nginx

Deploying a second dockerised app to my cloud server brought up a routing complexity in my infrastructure, leading to using Nginx as a reverse-proxy. Though straightforward to install and configure, encountering a default site binding to port 80 initially caused confusion, requiring deeper investigation into generic error messages for clarification.

Despite these complexities, the Sudoku app now stands ready for use and exploration.

[^3]: [sudoku.clj (github)](https://gist.github.com/ship561/3755868)
