/*
 * Copyright 2005 Werner Guttmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ctf.jdo.tc9x;

public class Car extends Product {
    private int _kw;
    private String _make;

    public final int getKw() { return _kw; }
    public final void setKw(final int kw) { _kw = kw; }

    public final String getMake() { return _make; }
    public final void setMake(final String make) { _make = make; }
}
