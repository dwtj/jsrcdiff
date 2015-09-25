A tool for comparing some basic AST differences between two similar Java source files.

The differences detected fall into one of six categories:

1. **AM:** Add a new method
2. **DM:** Delete a method
3. **CM:** Change the body of a method or its location within a class.
4. **AF:** Add a field
5. **DF:** Delete a field
6. **CFI:** Change the definition of a field initializer:
    i. Add an initialization on a field where there was none.
    ii. Deleting an initialization on a field where there was one.
    iii. Changing the initialization expression.
    iv. Changing a field's modifier (e.g. `private` to `public`).

To build and install the project to the local Maven repository:

```{.sh}
$ mvn clean install
```

