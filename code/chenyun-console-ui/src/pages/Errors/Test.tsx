import {Anchor, Col, Flex, Row} from "antd";
// interface AnchorItem {
//     key: string,
//     href: string,
//     title: string,
//     children: AnchorItem[]
// }
const Test = () => {

    const createUniqueId = (prefix: string, index: number) => `${prefix}-${index}`;

    const html: string = `<h1>关于橙云使用的编辑器</h1><pre><code class="language-java">Class ChenYun {
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}   </code></pre><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><h1>测试标题2</h1><p>测试行</p><p>测试行</p><h2>2.1</h2><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><p>测试行</p><h1>测试标题3</h1><h2>3.1</h2><h3>3.1.1</h3>`;

    // 创建一个DOMParser实例
    const parser = new DOMParser();

    // 解析HTML字符串为DOM文档
    const doc = parser.parseFromString(html, 'text/html');
    // 遍历所有的h标签
    const headings = doc.querySelectorAll('h1, h2, h3, h4, h5, h6');

    console.log("添加id前：：",headings)
    headings.forEach((heading, index) => {
        // 生成唯一的id
        const level = parseInt(heading.tagName.slice(1), 10); // 获取标签层级（1-6）
        const id = createUniqueId('chenyun-article', level * 1000 + index); // 假设每层最多有1000个标题，避免id冲突
        // 为标题添加id属性
        heading.id = id;
    })
    console.log("添加id后：：",headings)
    const str: string = doc.body.outerHTML
    console.log(str.length)
    console.log(str.slice(6, 740))

    // const htmlStr = str.slice(6, 740);
    // const docs = parser.parseFromString(htmlStr, 'text/html');
    // const headingss = docs.querySelectorAll('h1, h2, h3, h4, h5, h6');
    // const items: AnchorItem[] = [];
    // headingss.forEach((heading) => {
    //     if (heading.tagName === 'H1') {
    //         items.push({
    //             key: String(heading.id),
    //             href: `#${heading.id}`,
    //             title: String(heading.getHTML()),
    //             children: []
    //         })
    //     } else if (heading.tagName === 'H2') {
    //         items[items.length-1].children.push({
    //             key: String(heading.id),
    //             href: `#${heading.id}`,
    //             title: String(heading.getHTML()),
    //             children: []
    //         })
    //     } else if (heading.tagName === 'H3') {
    //         items[items.length-1].children[items[items.length-1].children.length-1].children.push({
    //             key: String(heading.id),
    //             href: `#${heading.id}`,
    //             title: String(heading.getHTML()),
    //             children: []
    //         })
    //     }
    // })

    const items = [
        {
            key: 'part-1',
            href: '#chenyun-article-1000',
            title: 'Part 1',
        },
        {
            key: 'part-2',
            href: '#2',
            title: 'Part 2',
        },
        {
            key: 'part-3',
            href: '#3',
            title: 'Part 3',
            children: [
                {
                    key: 'part-4',
                    href: '#4',
                    title: 'Part 4',
                },
            ]
        },
    ]


    return (
        <>
            <Flex>
                <div style={{border: '1px solid', width: '20vw', height: '500px'}}></div>
                <div style={{width:'80vw'}}>
                    <Row>
                        <Col span={21}>
                            <div id={"chenyun-article-1000"} style={{border: '1px solid', backgroundColor:'red', height: "500px"}}></div>
                            <div id={"2"} style={{border: '1px solid', backgroundColor:'green', height: "500px"}}></div>
                            <div id={"3"} style={{border: '1px solid', backgroundColor:'black', height: "500px"}}></div>
                            <div id={"4"} style={{border: '1px solid', backgroundColor:'white', height: "500px"}}></div>
                        </Col>
                        <Col span={3}>
                            <Anchor
                                onClick={(e) => e.preventDefault()}
                                items={items}
                            />
                        </Col>
                    </Row>
                </div>
            </Flex>
            {/*<Row>*/}
            {/*    <Col span={16}>*/}
            {/*        <div id="part-1" style={{ height: '100vh', background: 'rgba(255,0,0,0.02)' }} />*/}
            {/*        <div id="part-2" style={{ height: '100vh', background: 'rgba(0,255,0,0.02)' }} />*/}
            {/*        <div id="part-3" style={{ height: '100vh', background: 'rgba(0,0,255,0.02)' }} />*/}
            {/*    </Col>*/}
            {/*    <Col span={8}>*/}
            {/*        <Anchor*/}
            {/*            items={[*/}
            {/*                {*/}
            {/*                    key: 'part-1',*/}
            {/*                    href: '#part-1',*/}
            {/*                    title: 'Part 1',*/}
            {/*                },*/}
            {/*                {*/}
            {/*                    key: 'part-2',*/}
            {/*                    href: '#part-2',*/}
            {/*                    title: 'Part 2',*/}
            {/*                },*/}
            {/*                {*/}
            {/*                    key: 'part-3',*/}
            {/*                    href: '#part-3',*/}
            {/*                    title: 'Part 3',*/}
            {/*                },*/}
            {/*            ]}*/}
            {/*        />*/}
            {/*    </Col>*/}
            {/*</Row>*/}
        </>
    )
}

export default Test;
