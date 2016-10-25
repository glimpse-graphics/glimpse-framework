# Glimpse Framework Guidelines for Contributors

First of all, new contributors are always welcome in Glimpse Framework.

> Programming is a social activity. ― Robert C. Martin

## Issues

Please do not use [labels](https://github.com/GlimpseFramework/glimpse-framework/labels) other than:
* android—for issues related to Android implementation
* bug
* feature
* refactoring

### Bugs

If you have found a bug, don't hesitate to report it—even if you're not sure if it's a real bug.
False alarms are better than hidden bugs.

[Label](https://github.com/GlimpseFramework/glimpse-framework/labels) the issue as a **bug**.

### Feature Requests

If you'd like to see a new feature in Glimpse Framework,
just create a [new issue](https://github.com/GlimpseFramework/glimpse-framework/issues).
Each request will be taken under consideration.

[Label](https://github.com/GlimpseFramework/glimpse-framework/labels) the issue as a **feature**.

## Source Code

### Technical rules

* Indentation with tabs, and tabs only.
* Lines no longer than 160 characters—a maximum line length visible in a typical IDE without scrolling.
A tab counts as 4 characters.

### Keep Code Clean

The following rules are direct quotes from Uncle Bob's
_[Clean Code: A Handbook of Agile Software Craftsmanship](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)_:

* It is not enough for code to work.
* Leave the campground cleaner than you found it.
* A long descriptive name is better than a short enigmatic name.
A long descriptive name is better than a long descriptive comment.
* When you see commented-out code, delete it!
* Building a project should be a single trivial operation.

If you haven't read [Uncle Bob Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882) yet,
you probably should.

### Testing

Whenever you submit a pull request, make sure your changes are tested.

#### Code Coverage

Typically, [CodeCov](https://codecov.io/github/GlimpseFramework/glimpse-framework) will approve pull requests with:
* at least 85% overall coverage,
* at least 75% coverage of your changes.

However, these are only suggestions, and every change will treated individually.
Your pull request might be accepted even when the coverage is 0%, if you can justify it.
