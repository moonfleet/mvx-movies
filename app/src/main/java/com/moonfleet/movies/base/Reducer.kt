package com.moonfleet.movies.base

import java.lang.IllegalStateException

abstract class Reducer<T>(val initialState: T) {

    abstract fun reduce(initialState: T, action: Action): T

    protected fun incompatibleAction(action: Action, state: T) = IllegalStateException("Action $action unable to reduce state $state")

}