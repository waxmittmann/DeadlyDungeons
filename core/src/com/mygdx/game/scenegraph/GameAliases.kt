package com.mygdx.game.scenegraph

import com.mygdx.game.entities.SceneNodeData

typealias GameNode = SameNode<SceneNodeData>
typealias GameParentNode = ParentNode<SceneNodeData, SceneNodeData>
typealias GameLeaf = Leaf<SceneNodeData, SceneNodeData>
typealias GameRotation = Rotation<SceneNodeData, SceneNodeData>
typealias RelativeGameTranslation = RelativeTranslation<SceneNodeData, SceneNodeData>
typealias AbsoluteGameTranslation = AbsoluteTranslation<SceneNodeData, SceneNodeData>
