/*
 * Copyright (c) 2000, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

package sun.jvm.hotspot.utilities;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.oops.Metadata;
import sun.jvm.hotspot.oops.Oop;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.types.*;

/** This class determines to the best of its ability, and in a
    reasonably robust fashion, whether a given pointer is an intact
    oop or not. It does this by checking the integrity of the
    metaclass hierarchy. This is only intended for use in the
    debugging system. It may provide more resilience to unexpected VM
    states than the ObjectHeap code. */

public class RobustOopDeterminator {
  public static boolean oopLooksValid(OopHandle oop) {
    if (oop == null) {
      return false;
    }
    if (!VM.getVM().getUniverse().isIn(oop)) {
      return false;
    }
    try {
      // Try to instantiate the Klass
      Oop.getKlassForOopHandle(oop);
      return true;
    } catch (AddressException | WrongTypeException e) {
      return false;
    }
  }
}
