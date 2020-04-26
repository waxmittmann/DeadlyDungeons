package com.mygdx.game.util.linear

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.badlogic.gdx.graphics.g2d.Batch

class ProjectionSaver(private val projection: Option<WrappedMatrix>,
                      private val transform: Option<WrappedMatrix>) {
    companion object Factory {
        fun saveProjection(batch: Batch) =
                ProjectionSaver(
                        Some(
                                WrappedMatrix(
                                        batch.projectionMatrix)),
                        None)

        fun saveTransform(batch: Batch) =
                ProjectionSaver(
                        None,
                        Some(
                                WrappedMatrix(
                                        batch.transformMatrix)))

        fun saveBoth(batch: Batch) =
                ProjectionSaver(
                        Some(
                                WrappedMatrix.from(
                                        batch.projectionMatrix)),
                        Some(
                                WrappedMatrix.from(
                                        batch.transformMatrix)))

        fun <S>doThenRestore(batch: Batch): (() -> S) -> (S) = { s: () -> S ->
            val saver: ProjectionSaver = saveBoth(batch)
            val sv = s()
            saver.restore(batch)
            sv
        }
    }

    fun restore(batch: Batch) {
        projection.map { batch.projectionMatrix = it.get() }
        transform.map { batch.transformMatrix = it.get() }
    }
}