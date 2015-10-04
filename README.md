A tool for comparing some basic AST differences between two similar Java source files.

The differences detected fall into one of six categories:

1. **AM:** Add a new method
2. **DM:** Delete a method
3. **CM:** Change the body of a method.
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

To run the `Main` class:

```{.sh}
mvn exec:java
```

## Definitions

### Field Definitions

#### Field Key

We define a field key as a string concatenation of:

* type
* ID (AKA variable name)

#### Field Value

We define a field value as a string concatenation of:

* space-delimited qualifiers (e.g. "public static final")
* initializers

### Method Definitions

#### Method Key

We define a method key as a concatenated string of the method return type,
the method ID (AKA method name) and the variable types in the method
argument list (e.g. "int, String, HashMap<String, String>").

#### Method Value

We define a method value as a concatenated string of the IDs (AKA variable
names) of method arguments, qualifiers (e.g. "public static final"), and
the text body of the method.

**NOTE**: We don't actually parse the method body, opting instead of a naive
string comparison.

### Operator Definitions

#### AM: Add a new method

We define a method as "Added" when the method key is not present in class 1,
but the method key is present in class 2.

#### DM: Delete a method

We define a method as "Deleted" when the method key is present in class 1, but
the method key is not present in class 2.

#### CM: Change a method

We define a method as "Changed" when the method key is present in both class
1 and 2, but the method value has changed.

#### AF: Add a field

We define a field as "Added" if the field key is not present in class 1,
but the field key is present in class 2.

#### DF: Delete a field

We define a field as "Deleted" if the field key is present in class 1, but
the field key is not present in class 2.

#### CFI: Change field initializer

We define a field as "Changed" if the field key is present in both class 1
and 2, but the field value has changed.
