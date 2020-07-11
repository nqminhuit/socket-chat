import React from "react";

export class Input extends React.Component {
  constructor(props) {
    super(props);
    this.handleValueChange = this.handleValueChange.bind(this);
  }

  handleValueChange(event) {
    this.props.onInputChange(event.target.value);
  }

  render() {
    const inputValue = this.props.inputValue;
    return (
      <input
        id={this.props.id}
        className="form-control margin-bot-10px"
        type={this.props.type ? this.props.type : "text"}
        placeholder={this.props.placeholder}
        value={inputValue}
        onChange={this.handleValueChange}
      ></input>
    );
  }
}
