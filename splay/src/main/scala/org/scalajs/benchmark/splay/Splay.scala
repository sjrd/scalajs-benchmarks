/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2015, Nicolas Stucki   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \                               **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

// Copyright 2009 the V8 project authors. All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
//       notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
//       copyright notice, this list of conditions and the following
//       disclaimer in the documentation and/or other materials provided
//       with the distribution.
//     * Neither the name of Google Inc. nor the names of its
//       contributors may be used to endorse or promote products derived
//       from this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//
// This benchmark is based on a JavaScript log processing module used
// by the V8 profiler to generate execution time profiles for runs of
// JavaScript applications, and it effectively measures how fast the
// JavaScript engine is at allocating nodes and reclaiming the memory
// used for old nodes. Because of the way splay trees work, the engine
// also has to deal with a lot of changes to the large tree object
// graph.

// This is a Scala implementation of the Splay benchmark from:
// https://github.com/chromium/octane/blob/master/splay.js

// Ported by Nicolas Stucki to Scala.js.

package org.scalajs.benchmark.splay

import scala.annotation.tailrec
import scala.scalajs.js

object Splay extends org.scalajs.benchmark.Benchmark {
  override def prefix: String = "Splay"

  def run(): Unit =
    splayRun()

  override def setUp(): Unit =
    splaySetup()

  override def tearDown(): Unit =
    splayTearDown()

  override def minWarmUpTime: Int = 50

  override def minRunTime: Int = 350

  //// Configuration.
  private final val kSplayTreeSize = 8000
  private final val kSplayTreeModifications = 80
  private final val kSplayTreePayloadDepth = 5

  private var splayTree: SplayTree[js.Dynamic] = null

  private def generatePayloadTree(depth: Int, tag: String): js.Dynamic = {
    if (depth == 0) {
      js.Dynamic.literal(
        array = js.Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
        string = "String for key " + tag + " in leaf node"
      )
    } else {
      js.Dynamic.literal(
        left =  generatePayloadTree(depth - 1, tag),
        right = generatePayloadTree(depth - 1, tag)
      )
    }
  }

  private var seed: Int = 49734321

  private def resetRandomSeed(): Unit =
    seed = 49734321

  private def nextRandomInt(): Int = {
    // Robert Jenkins' 32 bit integer hash function.
    var seed = this.seed
    seed = (seed + 0x7ed55d16) + (seed << 12)
    seed = (seed ^ 0xc761c23c) ^ (seed >>> 19)
    seed = (seed + 0x165667b1) + (seed << 5)
    seed = (seed + 0xd3a2646c) ^ (seed << 9)
    seed = (seed + 0xfd7046c5) + (seed << 3)
    seed = (seed ^ 0xb55a4f09) ^ (seed >>> 16)
    seed = seed & 0x7ffffff
    this.seed = seed
    seed
  }

  private def generateKey(): Int = {
    // The benchmark framework guarantees that Math.random is deterministic.
    nextRandomInt()
  }

  private def insertNewNode(): Int = {
    // Insert new node with a unique key.
    var key = 0
    do {
      key = generateKey()
    } while (splayTree.find(key) != null)
    val payload = generatePayloadTree(kSplayTreePayloadDepth, key.toString)
    splayTree.insert(key, payload)
    key
  }

  private def splaySetup(): Unit = {
    resetRandomSeed()
    splayTree = new SplayTree
    for (i <- 0 until kSplayTreeSize)
      insertNewNode()
  }

  private def splayTearDown(): Unit = {
    // Allow the garbage collector to reclaim the memory
    // used by the splay tree no matter how we exit the
    // tear down function.
    val keys = splayTree.exportKeys
    splayTree = null

    // Verify that the splay tree has the right size.
    val length = keys.length
    if (length != kSplayTreeSize)
      throw new Exception("Splay tree has wrong size")

    // Verify that the splay tree has sorted, unique keys.
    for (i <- 0 until length - 1) {
      if (keys(i) >= keys(i + 1))
        throw new Exception("Splay tree not sorted")
    }
  }

  private def splayRun(): Unit = {
    // Replace a few nodes in the splay tree.
    for (i <- 0 until kSplayTreeModifications) {
      val key = insertNewNode()
      val greatest = splayTree.findGreatestLessThan(key)
      if (greatest == null) splayTree.remove(key)
      else splayTree.remove(greatest.key)
    }
  }
}

/**
 * Constructs a Splay tree.  A splay tree is a self-balancing binary
 * search tree with the additional property that recently accessed
 * elements are quick to access again. It performs basic operations
 * such as insertion, look-up and removal in O(log(n)) amortized time.
 *
 * @constructor
 */
class SplayTree[T <: AnyRef] {

  /**
   * Pointer to the root node of the tree.
   *
   * @type {SplayTree.Node}
   * @private
   */
  private var root: SplayTree.Node[T] = null

  /**
   * @return {boolean} Whether the tree is empty.
   */
  @inline
  def isEmpty: Boolean = root == null

  /**
   * Inserts a node into the tree with the specified key and value if
   * the tree does not already contain a node with the specified key. If
   * the value is inserted, it becomes the root of the tree.
   *
   * @param {number} key Key to insert into the tree.
   * @param {*} value Value to insert into the tree.
   */
  def insert(key: Int, value: T): Unit = {
    if (isEmpty) {
      root = new SplayTree.Node(key, value)
    } else {
      // Splay on the key to move the last node on the search path for
      // the key to the root of the tree.
      splay(key)
      if (root.key != key) {
        val node = new SplayTree.Node(key, value)
        if (key > root.key) {
          node.left = root
          node.right = root.right
          root.right = null
        } else {
          node.right = root
          node.left = root.left
          root.left = null
        }
        root = node
      }
    }
  }

  /**
   * Removes a node with the specified key from the tree if the tree
   * contains a node with this key. The removed node is returned. If the
   * key is not found, an exception is thrown.
   *
   * @param {number} key Key to find and remove from the tree.
   * @return {SplayTree.Node} The removed node.
   */
  def remove(key: Int): SplayTree.Node[T] = {
    if (isEmpty) {
      throw new Exception(s"Key not found: $key")
    } else {
      splay(key)
      if (root.key != key) {
        throw new Exception(s"Key not found: $key")
      } else {
        val removed = root
        if (root.left == null) {
          root = root.right
        } else {
          val right = root.right
          root = root.left
          // Splay to make sure that the new root has an empty right child.
          splay(key)
          // Insert the original right child as the right child of the new
          // root.
          root.right = right
        }
        removed
      }
    }
  }

  /**
   * Returns the node having the specified key or null if the tree doesn't contain
   * a node with the specified key.
   *
   * @param {number} key Key to find in the tree.
   * @return {SplayTree.Node} Node having the specified key.
   */
  def find(key: Int): SplayTree.Node[T] = {
    if (isEmpty) {
      null
    } else {
      splay(key)
      if (root.key == key) root
      else null
    }
  }

  /**
   * @return {SplayTree.Node} Node having the maximum key value.
   */
  def findMax(): SplayTree.Node[T] = findMax(root)

  def findMax(startNode: SplayTree.Node[T]): SplayTree.Node[T] = {
    if (isEmpty) {
      null
    } else {
      var current = startNode
      while (current.right != null)
        current = current.right
      current
    }
  }

  /**
   * @return {SplayTree.Node} Node having the maximum key value that
   *     is less than the specified key value.
   */
  def findGreatestLessThan(key: Int): SplayTree.Node[T] = {
    if (isEmpty) {
      null
    } else {
      // Splay on the key to move the node with the given key or the last
      // node on the search path to the top of the tree.
      splay(key)
      // Now the result is either the root node or the greatest node in
      // the left subtree.
      if (root.key < key) {
        root
      } else if (root.left != null) {
        findMax(root.left)
      } else {
        null
      }
    }
  }

  /**
   * @return {Array<*>} An array containing all the keys of tree's nodes.
   */
  def exportKeys: Array[Int] = {
   var b = Array.newBuilder[Int]
   if (!isEmpty)
      root.traverse(node => b += node.key)
   b.result()
  }

  /**
   * Perform the splay operation for the given key. Moves the node with
   * the given key to the top of the tree.  If no node has the given
   * key, the last node on the search path is moved to the top of the
   * tree. This is the simplified top-down splaying algorithm from:
   * "Self-adjusting Binary Search Trees" by Sleator and Tarjan
   *
   * @param {number} key Key to splay the tree on.
   * @private
   */
  private def splay(key: Int): Unit = {
    if (!isEmpty) {
      // Create a dummy node.  The use of the dummy node is a bit
      // counter-intuitive: The right child of the dummy node will hold
      // the L tree of the algorithm.  The left child of the dummy node
      // will hold the R tree of the algorithm.  Using a dummy node, left
      // and right will always be nodes and we avoid special cases.
      val dummy = SplayTree.Node.newDummy[T]

      def assemble(current: SplayTree.Node[T], left: SplayTree.Node[T],
          right: SplayTree.Node[T]): Unit = {
        left.right = current.left
        right.left = current.right
        current.left = dummy.right
        current.right = dummy.left
        root = current
      }

      @tailrec def loop(current0: SplayTree.Node[T], left: SplayTree.Node[T],
          right: SplayTree.Node[T]): Unit = {
        if (key < current0.key && current0.left != null) {
          val newCurrent = {
            if (key < current0.left.key) {
              // Rotate right.
              val tmp = current0.left
              current0.left = tmp.right
              tmp.right = current0
              if (tmp.left == null) {
                assemble(tmp, left, right)
                return
              }
              tmp
            } else {
              current0
            }
          }
          // Link right.
          right.left = newCurrent
          loop(newCurrent.left, left, newCurrent)
        } else if (key > current0.key && current0.right != null) {
          val newCurrent ={
            if (key > current0.right.key) {
              // Rotate left.
              val tmp = current0.right
              current0.right = tmp.left
              tmp.left = current0
              if (tmp.right == null) {
                assemble(tmp, left, right)
                return
              }
              tmp
            } else {
              current0
            }
          }
          // Link left.
          left.right = newCurrent
          loop(newCurrent.right, newCurrent, right)
        } else {
          assemble(current0, left, right)
        }
      }
      loop(root, dummy, dummy)
    }
  }
}

object SplayTree {
  /**
    * Constructs a Splay tree node.
    *
    * @param {number} key Key.
    * @param {*} value Value.
    */
  class Node[T <: AnyRef](val key: Int, val value: T) {

    /**
      * @type {SplayTree.Node}
      */
    var left: Node[T] = null.asInstanceOf[Node[T]]

    /**
      * @type {SplayTree.Node}
      */
    var right: Node[T] = null.asInstanceOf[Node[T]]

    /**
      * Performs an ordered traversal of the subtree starting at
      * this SplayTree.Node.
      *
      * @param {function(SplayTree.Node)} f Visitor function.
      * @private
      */
    def traverse(f: Node[T] => Unit): Unit = {
      var current = this
      while (current != null) {
        val left = current.left
        if (left != null) left.traverse(f)
        f(current)
        current = current.right
      }
    }
  }

  object Node {
    @inline
    def newDummy[T <: AnyRef]: Node[T] = new Node(0, null.asInstanceOf[T])
  }
}
