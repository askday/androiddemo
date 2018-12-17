import React, { Component } from 'react';
import { Treebeard } from 'react-treebeard';
import { HttpUtil } from '../Common/HttpUtil';
import treeDecorator from './TreeDecorator';
import './index.css';

export default class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      grid: [],
    };
  }

  componentDidMount() {
    console.log('==========mount');
    this._loadTreeData();
  }

  componentWillUnmount() {
    console.log('==========unmount');
  }

  _loadTreeData = () => {
    HttpUtil.post('list', { a: 123 }).then((result) => {
      console.log(result);
      const { data } = result;
      this.setState({ data });
    }).catch((err) => {
      console.log(err);
    });
  }

  _updateGridData = (node) => {
    // node.children && node.children.push({
    //   name: '123',
    // })
    HttpUtil.post('grid', { a: 123 }).then((result) => {
      console.log(result);
      const { data } = result;
      this.setState({ grid: data });
    }).catch((err) => {
      console.log(err);
    });
  }

  _onTreeToggle = (node, toggled) => {
    if (this.state.cursor) { this.state.cursor.active = false; }
    node.active = true;
    if (node.children && node.children.length > 0) {
      node.toggled = toggled;
    } else {
      console.log('==========', node);
      this._updateGridData(node);
    }
    this.setState({ cursor: node });
  }

  render() {
    const { data, grid } = this.state;
    const tableData = [];
    for (let i = 0; i < grid.length; i++) {
      const cell = grid[i];
      if (i % 13 === 0) {
        tableData.push([]);
      }
      tableData[tableData.length - 1].push(cell);
    }
    return (
      <div className="container" >
        <div className="left">
          <Treebeard
            data={data}
            decorators={treeDecorator}
            onToggle={this._onTreeToggle}
          />
        </div>
        <div className="right">
          <table>
            <tbody>
              {
                tableData && tableData.map((rows, index) => {
                  return (
                    <tr key={index}>
                      {
                        rows && rows.map((column, idx) => {
                          const bg1 = { background: 'red', display: 'flex', flex: column.bb };
                          const bg2 = { background: 'blue', display: 'flex', flex: column.cell };
                          const bg3 = { background: 'black', display: 'flex', flex: column.fold };

                          return (
                            <td key={idx}>
                              <div className="tdcontainer">
                                <div className="tdbg">
                                  <div style={bg1} />
                                  <div style={bg2} />
                                  <div style={bg3} />
                                </div>
                                <div className="tbcontent">
                                  <div>{column.name}</div>
                                  <div>{column.bb}</div>
                                  <div>{column.cell}</div>
                                  <div>{column.fold}</div>
                                </div>
                              </div>
                            </td>
                          );
                        })
                      }
                    </tr>
                  )
                })
              }
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}
