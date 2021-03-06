//import liraries
import React, { Component } from 'react';
import '../App.css';

// create a component
export default class MakeData extends Component {
  constructor(props) {
    super(props);
    const row = 13;
    const colum = 13;
    const data = [];
    for (let i = 0; i < row; i++) {
      const subData = [];
      data.push(subData);
      for (let j = 0; j < colum; j++) {
        subData.push({
          name: '-.-',
          bb: '-.-',
          cell: '-.-',
          fold: '-.-',
        });
      }
    }
    this.data = data;
  }

  handleInputChange = (e) => {
    const { target } = e;
    const { name, value } = target;
    const idxs = name.split('_');
    const row = idxs[0];
    const column = idxs[1];
    const type = idxs[2];
    const { data } = this;
    data[row][column][type] = value;
  }

  onSubmit = (e) => {
    e.preventDefault();
    console.log(JSON.stringify(this.data));
    fetch("http://localhost:3005/submit ", {
      method: 'POST',
      // headers: {
      //   'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
      // },
      body: JSON.stringify(this.data)
    }).then(response => {
      return response.json();
    }).then(data => {
      console.log(data);
    }).catch(error => {
      console.log(error)
    });
  }

  render() {
    const { data } = this;
    return (
      <div className="App">
        <form className="App-header" onSubmit={this.onSubmit} >
          {
            data.map((row, index) => (
              <div key={index} style={{ display: 'flex' }}>
                {
                  row.map((coloum, idx) => (
                    <div key={idx} style={{ border: '1px blue solid', margin: 2 }}>
                      <div>
                        <input
                          name={`${index}_${idx}_name`}
                          defaultValue={coloum.name}
                          onChange={this.handleInputChange}
                        />
                      </div>
                      <div>
                        <input
                          name={`${index}_${idx}_bb`}
                          defaultValue={coloum.bb}
                          onChange={this.handleInputChange}
                        />
                      </div>
                      <div>
                        <input
                          name={`${index}_${idx}_cell`}
                          defaultValue={coloum.cell}
                          onChange={this.handleInputChange}
                        />
                      </div>
                      <div>
                        <input
                          name={`${index}_${idx}_fold`}
                          defaultValue={coloum.fold}
                          onChange={this.handleInputChange}
                        />
                      </div>
                    </div>
                  ))
                }
              </div>

            ))
          }
          <input type="submit" value="提交" />
        </form>
      </div>
    );
  }
}
