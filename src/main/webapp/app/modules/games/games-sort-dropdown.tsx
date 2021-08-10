import React, { useState } from 'react';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

export interface IGamesSortProps {
  handleSort: (sort, order) => void,
}

const GamesSortDropdown = (props: IGamesSortProps) => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => setDropdownOpen(prevState => !prevState);

  return (
    <Dropdown isOpen={dropdownOpen} toggle={toggleDropdown} style={{margin: "15px"}}>
      <DropdownToggle color="light" caret>Sort by</DropdownToggle>
      <DropdownMenu>
        <DropdownItem onClick={() => props.handleSort("price", "desc")}>Price: High to Low</DropdownItem>
        <DropdownItem onClick={() => props.handleSort("price", "asc")}>Price: Low to High</DropdownItem>
        <DropdownItem onClick={() => props.handleSort("userRating", "desc")}>Highest Rated</DropdownItem>
        <DropdownItem onClick={() => props.handleSort("name", "asc")}>Name: A to Z</DropdownItem>
        <DropdownItem onClick={() => props.handleSort("name", "desc")}>Name: Z to A</DropdownItem>
      </DropdownMenu>
    </Dropdown>
  );
};

export default GamesSortDropdown;
