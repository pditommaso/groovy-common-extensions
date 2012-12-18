/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tests

import spock.lang.Specification

/**
 * User: dwoods
 * Date: 11/20/12
 */
class NodeToMapTests extends Specification {
    def 'check xml string to node to map coercion with children'() {
        given:
        def xml = '''<dan id="1234" attr="attrValue">
                    |  <key value="val" />
                    |  <key value="val2" />
                    |</dan>'''.stripMargin()
        def map = new XmlSlurper().parseText( xml ).toMap()

        expect:
        map instanceof Map
        map.dan._children.size() == 2
        map.dan._children.key[1].value == 'val2'
    }

    def 'check xml string to node to map coercion with children and specified childKey'() {
        given:
        def xml = '''<dan id="1234" attr="attrValue">
                    |  <key value="val" />
                    |  <key value="val2" />
                    |</dan>'''.stripMargin()
        def map = new XmlSlurper().parseText( xml ).toMap( 'kids' )

        expect:
        map instanceof Map
        map.dan.kids.size() == 2
        map.dan.kids.key[1].value == 'val2'
    }

    def 'check map value'() {
        given:
            def xmlstr = '<dan value="a"><subnode><item value="a"/></subnode></dan>'
            def xml = new XmlSlurper().parseText( xmlstr )
            def map = xml.toMap()
        expect:
            map == [dan:[value:'a',_children:[[subnode:[_children:[[item:[value:'a']]]]]]]]
    }

    def 'check map value with childKey'() {
        given:
            def xmlstr = '<dan value="a"><subnode><item value="a"/></subnode></dan>'
            def xml = new XmlSlurper().parseText( xmlstr )
            def map = xml.toMap( 'kids' )
        expect:
            map == [dan:[value:'a',kids:[[subnode:[kids:[[item:[value:'a']]]]]]]]
    }
}
