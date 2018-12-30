package org.inferred.freebuilder.processor;

import com.google.common.collect.ImmutableList;

import org.inferred.freebuilder.processor.util.Excerpt;
import org.inferred.freebuilder.processor.util.FieldAccess;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeMirror;

/** Datatype about a property of a {@link Datatype}. */
public abstract class Property {

  /** Returns the type of the property. */
  public abstract TypeMirror getType();

  /** Returns the boxed form of {@link #getType()}, or null if type is not primitive. */
  @Nullable public abstract TypeMirror getBoxedType();

  /** Returns the name of the property, e.g. myProperty. */
  public abstract String getName();

  /** Returns the field name that stores the property, e.g. myProperty. */
  public FieldAccess getField() {
    return new FieldAccess(getName());
  }

  /** Returns the capitalized name of the property, e.g. MyProperty. */
  public abstract String getCapitalizedName();

  /** Returns the name of the property in all-caps with underscores, e.g. MY_PROPERTY. */
  public abstract String getAllCapsName();

  /** Returns true if getters start with "get"; setters should follow suit with "set". */
  public abstract boolean isUsingBeanConvention();

  /** Returns the name of the getter for the property, e.g. getMyProperty, or isSomethingTrue. */
  public abstract String getGetterName();

  /**
   * Returns the code generator to use for this property, or null if no generator has been picked
   * (i.e. when passed to {@link PropertyCodeGenerator.Factory#create}.
   */
  @Nullable public abstract PropertyCodeGenerator getCodeGenerator();

  /**
   * Returns true if a cast to this property type is guaranteed to be fully checked at runtime.
   * This is true for any type that is non-generic, raw, or parameterized with unbounded
   * wildcards, such as {@code Integer}, {@code List} or {@code Map<?, ?>}.
   */
  public abstract boolean isFullyCheckedCast();

  /**
   * Returns a list of annotations that should be applied to the accessor methods of this
   * property; that is, the getter method, and a single setter method that will accept the result
   * of the getter method as its argument. For a list, for example, that would be getX() and
   * addAllX().
   */
  public abstract ImmutableList<Excerpt> getAccessorAnnotations();

  public Property.Builder toBuilder() {
    return new Builder().mergeFrom(this);
  }

  /** Builder for {@link Property}. */
  public static class Builder extends Property_Builder {}
}
