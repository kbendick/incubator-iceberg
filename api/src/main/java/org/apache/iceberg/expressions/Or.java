/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iceberg.expressions;

import java.util.Objects;

public class Or implements Expression {
  private final Expression left;
  private final Expression right;

  Or(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  public Expression left() {
    return left;
  }

  public Expression right() {
    return right;
  }

  @Override
  public Operation op() {
    return Expression.Operation.OR;
  }

  @Override
  public Expression negate() {
    // not(or(a, b)) => and(not(a), not(b))
    return Expressions.and(left.negate(), right.negate());
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof Or)) {
      return false;
    }

    Or that = (Or) other;
    return Objects.equals(left, that.left) &&
        Objects.equals(right, that.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, right);
  }

  @Override
  public String toString() {
    return String.format("(%s or %s)", left, right);
  }
}
