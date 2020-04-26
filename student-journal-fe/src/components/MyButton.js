import React from 'react';
import { styled } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';

const MyButton = styled(Button)({
    background: '#ccc',
    border: 0,
    borderRadius: 3,
    boxShadow: '0 3px 5px 2px rgba(0, 0, 0, .3)',
    color: 'black',
    height: 48,
    padding: '0 30px',
    margin: '5px 10px 10px 435px'
});

export default function StyledComponents() {
    return <MyButton>Styled Components</MyButton>;
}