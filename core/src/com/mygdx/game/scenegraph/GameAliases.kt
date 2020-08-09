package com.mygdx.game.scenegraph

import com.mygdx.game.entities.SceneNodeData

typealias GameNode = Node<SceneNodeData>
typealias GameParentNode = ParentNode<SceneNodeData>
typealias GameLeaf = Leaf<SceneNodeData>
typealias GameRotation = Rotation<SceneNodeData>
typealias RelativeGameTranslation = RelativeTranslation<SceneNodeData>
typealias AbsoluteGameTranslation = AbsoluteTranslation<SceneNodeData>
