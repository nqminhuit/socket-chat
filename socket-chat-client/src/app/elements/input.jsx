import React from "react";

export class Input extends React.Component {
  constructor(props) {
    super(props);
    this.handleValueChange = this.handleValueChange.bind(this);
    this.handleOnKeyPress = this.handleOnKeyPress.bind(this);
  }

  handleValueChange(event) {
    this.props.onInputChange(event.target.value);
  }

  handleOnKeyPress(event) {
    this.props.onKeyPressInput(event.key);
  }

  render() {
    return !this.props.onKeyPressInput ? (
      <input
        id={this.props.id}
        className="form-control margin-bot-10px"
        type={this.props.type ? this.props.type : "text"}
        placeholder={this.props.placeholder}
        onChange={this.handleValueChange}
      ></input>
    ) : (
      <input
        id={this.props.id}
        className="form-control margin-bot-10px"
        type={this.props.type ? this.props.type : "text"}
        placeholder={this.props.placeholder}
        onChange={this.handleValueChange}
        onKeyPress={this.handleOnKeyPress}
      ></input>
    );
  }
}
