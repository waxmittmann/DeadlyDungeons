package com.mygdx.game.util.linear

import arrow.core.*
import com.badlogic.gdx.graphics.g2d.Batch
import java.lang.RuntimeException

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

        fun <S>doThenRestoreWithTransformProjection(batch: Batch): (
                (WrappedMatrix, WrappedMatrix) -> S) -> (S) = { s: (WrappedMatrix, WrappedMatrix) ->
        S ->
            val saver: ProjectionSaver = saveBoth(batch)
            val sv = s(saver.transform.getOrElse { throw RuntimeException("!!!") },
                    saver.projection.getOrElse { throw RuntimeException("!!!!")})
            saver.restore(batch)
            sv
        }
    }

    fun restore(batch: Batch) {
        projection.map { batch.projectionMatrix = it.matrix() }
        transform.map { batch.transformMatrix = it.matrix() }
    }
}